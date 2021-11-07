import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Tag } from '@data/model/tag.model';
import { TagDataService } from '@data/service/tag-data.service';

@Component({
  selector: 'app-tag-form',
  templateUrl: './tag-form.component.html',
  styles: [],
})
export class TagFormComponent implements OnInit {
  form: FormGroup;
  id: number;
  tag: Tag = new Tag(null!, null!, null!);
  mode: string;
  isLoading: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private tagDataService: TagDataService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {
    this.isLoading = false;
    this.mode = '';
    this.form = this.formBuilder.group({
      name: ['', [Validators.required, Validators.maxLength(20)]],
      description: ['', Validators.maxLength(100)],
    });
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((params) => {
      if (params.get('id')) {
        this.id = Number(params.get('id'));
        this.tagDataService.getTagById(this.id).subscribe((tag: Tag) => {
          this.tag = tag;
          this.mode = 'update';
          this.setTagToForm();
        });
      } else {
        this.mode = 'create';
        this.tag = new Tag(null!, '', '');
      }
    });
  }

  setTagToForm(): void {
    this.form.controls['name'].setValue(this.tag.name);
    this.form.controls['description'].setValue(this.tag.description);
  }

  setFormToTag(): void {
    this.tag.name = this.form.controls['name'].value;
    this.tag.description = this.form.controls['description'].value;
  }

  disableSubmit(): boolean {
    return this.form.invalid || this.isLoading === true || this.form.pristine;
  }

  disableReset(): boolean {
    return this.isLoading === true || this.form.pristine;
  }

  onSubmit(): void {
    this.isLoading = true;
    this.setFormToTag();

    if (this.mode === 'create') {
      this.tagDataService.createTag(this.tag).subscribe(
        (tag: Tag) => {
          if (tag.id) {
            this.router.navigate(['/admin/tags']);
          } else {
            this.isLoading = false;
          }
        },
        () => {
          this.isLoading = false;
        }
      );
    } else if (this.mode === 'update') {
      this.tagDataService.updateTag(this.tag).subscribe(
        (tag: Tag) => {
          if (tag.id) {
            this.router.navigate(['/admin/tags']);
          } else {
            this.isLoading = false;
          }
        },
        () => {
          this.isLoading = false;
        }
      );
    } else {
      this.isLoading = false;
    }
  }

  resetForm(): void {
    this.setTagToForm();
    this.form.markAsPristine();
  }
}
