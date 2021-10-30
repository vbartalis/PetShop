import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Role } from '@data/model/role.model';
import { User } from '@data/model/user.model';
import { RoleDataService } from '@data/service/role-data.service';
import { UserDataService } from '@data/service/user-data.service';
import { faCalendar, faTimes } from '@fortawesome/free-solid-svg-icons';
import { NgbDateStruct, NgbTypeaheadSelectItemEvent } from '@ng-bootstrap/ng-bootstrap';
import { clone, isEqual } from 'lodash';
import { OperatorFunction, Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styles: [],
})
export class AccountDetailsComponent implements OnInit {
  faCalendar = faCalendar;
  faTimes = faTimes;

  form: FormGroup;
  id: number;
  roles: Role[];
  user: User = new User(null!, null!, null!, null!, []);
  userRoles: Role[] = [];

  oldDate: NgbDateStruct;
  newDate: NgbDateStruct;

  submitted: boolean;
  errorMessage: string;

  constructor(
    private formBuilder: FormBuilder,
    private userDataService: UserDataService,
    private roleDataService: RoleDataService,
    private activeRoute: ActivatedRoute,
    private router: Router,
    private datePipe: DatePipe
  ) {
    this.submitted = false;
    this.form = this.formBuilder.group({
      isLocked: [false],
      date: ['', [Validators.required]],
      rolesArray: new FormArray([]),
      typeaheadRole: [''],
    });
  }

  ngOnInit(): void {
    this.activeRoute.paramMap.subscribe((params) => {
      this.id = Number(params.get('userId'));
      this.userDataService.getUserById(this.id).subscribe((user: User) => {
        this.user = user;
        this.setUserToForm();
      });
    });
    this.roleDataService.getRoles().subscribe((roles: Role[]) => {
      this.roles = roles;
    });
  }

  convertDate(date: Date): string {
    return this.datePipe.transform(date, "yyyy-MM-dd'T'HH:mm:ss.SSSZ") ?? '??';
  }

  get rolesArray(): FormArray {
    return this.form.get('rolesArray') as FormArray;
  }

  setUserToForm(): void {
    this.form.controls['isLocked'].setValue(this.user.isLocked);
    const date = new Date(this.user.expiration);
    this.form.controls['date'].setValue({ year: date.getFullYear(), month: date.getMonth() + 1, day: date.getDate() });
    this.form.controls['typeaheadRole'].setValue('');

    this.userRoles = clone(this.user.roles);

    this.rolesArray.clear();
    this.userRoles.forEach((role: Role, i: number) => {
      this.rolesArray.push(
        this.formBuilder.group({
          id: '',
          name: '',
        })
      );
      const group = (this.form.controls['rolesArray'] as FormGroup).controls[i.toString()] as FormGroup;
      group.controls['id'].setValue(role.id);
      group.controls['name'].setValue(role.name);
    });
  }

  setFormToUser(): void {
    this.user.isLocked = this.form.controls['isLocked'].value;
    this.newDate = this.form.controls['date'].value;
    const date = new Date();
    date.setFullYear(this.newDate.year);
    date.setMonth(this.newDate.month - 1);
    date.setDate(this.newDate.day);
    this.user.expiration = date;
  }

  disableSubmit(): boolean {
    return this.form.invalid || this.submitted === true || (this.form.pristine && this.isClean());
  }

  disableReset(): boolean {
    return this.submitted === true || (this.form.pristine && this.isClean());
  }

  isClean(): boolean {
    if (isEqual(this.user.roles, this.userRoles)) {
      return true;
    } else {
      return false;
    }
  }

  onSubmit(): void {
    this.submitted = true;
    this.setFormToUser();
    this.userDataService.updateUser(this.user).subscribe(
      (user: User) => {
        if (user.id) {
          this.user = user;
          this.router.navigate(['.'], { relativeTo: this.activeRoute.parent });
        } else {
          this.submitted = false;
        }
      },
      (error) => {
        this.submitted = false;
        // Validation errors
        if (error.status === 422) {
          this.errorMessage = error.error.status;
        }
      }
    );
  }

  resetForm(): void {
    this.setUserToForm();
    this.form.markAsPristine();
  }

  formatter = (role: Role) => role.name;

  search: OperatorFunction<string, readonly { id: any; name: any }[]> = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map((term) => this.roles.filter((role) => new RegExp(term, 'mi').test(role.name)).slice(0, 10))
    );

  selectTypeaheadRole($event: NgbTypeaheadSelectItemEvent<any>, input: HTMLInputElement): void {
    $event.preventDefault();
    const role = $event.item;

    // if tag is not already in post then push it
    if (!this.user.roles.some((r) => r.id === role.id)) {
      this.userRoles.push(role);
    }
    input.value = '';
  }

  removeRole(role: Role): void {
    this.userRoles = this.userRoles.filter((r) => r.id !== role.id);
  }

  isValid(field: string): boolean {
    let isValid = false;

    // If the field is not touched and invalid, it is considered as initial loaded form. Thus set as true
    if (this.form.controls[field].touched === false) {
      isValid = true;
    } else if (
      (this.form.controls[field].touched === true && this.form.controls[field].valid === true) ||
      this.form.controls[field].pending
    ) {
      // If the field is touched and valid value, then it is considered as valid.
      isValid = true;
    }

    return isValid;
  }
}
