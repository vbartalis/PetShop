import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AbstractControlOptions, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Role } from '@data/model/role.model';
import { User } from '@data/model/user.model';
import { RoleDataService } from '@data/service/role-data.service';
import { UserDataService } from '@data/service/user-data.service';
import { faCalendar } from '@fortawesome/free-regular-svg-icons';
import { faTimes } from '@fortawesome/free-solid-svg-icons';
import { PasswordValidator } from '@modules/user/user-profile/validator/password.validator';
import { NgbActiveModal, NgbDateStruct, NgbTypeaheadSelectItemEvent } from '@ng-bootstrap/ng-bootstrap';
import { Observable, OperatorFunction } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';

@Component({
  selector: 'app-account-new',
  templateUrl: './account-new.component.html',
  styles: [],
})
export class AccountNewComponent implements OnInit {
  form: FormGroup;

  date: NgbDateStruct;

  faCalendar = faCalendar;
  faTimes = faTimes;

  roles: Role[];

  user: User;
  submitted: boolean;
  error: string;

  constructor(
    public activeModal: NgbActiveModal,
    private formBuilder: FormBuilder,
    private userDataService: UserDataService,
    private roleDataService: RoleDataService,
    private datePipe: DatePipe
  ) {
    this.submitted = false;
    this.form = this.formBuilder.group(
      {
        username: ['', [Validators.required, Validators.maxLength(30)]],
        isLocked: ['', [Validators.required]],
        date: [''],
        role: [''],
        password: ['', [Validators.required, Validators.maxLength(30)]],
        confirmPassword: ['', [Validators.required, Validators.maxLength(30)]],
      },
      {
        validator: PasswordValidator.passwordMatchValidator,
      } as AbstractControlOptions
    );
  }

  ngOnInit(): void {
    this.user = new User(null!, null!, true, null!, []);
    this.user.roles = [];
    this.initForm();
    this.roleDataService.getRoles().subscribe((roles: Role[]) => {
      this.roles = roles;
    });
    this.error = '';
  }

  convertDate(date: Date): string {
    return this.datePipe.transform(date, "yyyy-MM-dd'T'HH:mm:ss.SSSZ") ?? '??';
  }

  setFormToUser(): void {
    this.user.username = this.form.controls['username'].value;
    this.user.isLocked = this.form.controls['isLocked'].value;
    this.date = this.form.controls['date'].value;

    const date = new Date();
    date.setFullYear(this.date.year);
    date.setMonth(this.date.month - 1);
    date.setDate(this.date.day);
    // this.user.expiration = this.convertDate(date.toString());
    this.user.expiration = date;

    this.user.password = this.form.controls['password'].value;
  }

  //todo
  disableSubmit(): boolean {
    return false;
  }

  initForm(): void {
    this.form.controls['isLocked'].setValue(false);
    const date = new Date();
    this.form.controls['date'].setValue({ year: date.getFullYear(), month: date.getMonth() + 1, day: date.getDate() });
  }

  onSubmit(): void {
    this.submitted = true;
    this.setFormToUser();
    this.userDataService.createUserByid(this.user).subscribe(
      (user: User) => {
        if (user.id) {
          this.user = user;
          this.activeModal.close('success');
        } else {
          this.submitted = false;
        }
      },
      (error) => {
        this.submitted = false;
        // Validation errors
        if (error.status === 422) {
          this.error = error.error.error;
        }
      }
    );
  }

  formatter = (role: Role) => role.name;

  search: OperatorFunction<string, readonly { id: any; name: any }[]> = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map((term) => this.roles.filter((role) => new RegExp(term, 'mi').test(role.name)).slice(0, 10))
      // tslint:disable-next-line: semicolon
    );

  selectTypeaheadRole($event: NgbTypeaheadSelectItemEvent<any>, input: HTMLInputElement): void {
    $event.preventDefault();
    const role = $event.item;

    // if tag is not already in post then push it
    if (!this.user.roles.some((r) => r.id === role.id)) {
      this.user.roles.push(role);
    }
    input.value = '';
  }

  removeRole(role: Role): void {
    this.user.roles = this.user.roles.filter((r) => r.id !== role.id);
  }
}
