import { Component, Input, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { WebcamImage } from 'ngx-webcam';
import { Subscription } from 'rxjs';
import { UploadService } from '../upload.service';
import { dataToImage } from '../utils';
import { Post } from '../models';
import { Router } from '@angular/router';

@Component({
  selector: 'app-picture',
  templateUrl: './picture.component.html',
  styleUrl: './picture.component.css'
})
export class PictureComponent implements OnInit{
  
  // TODO: Task 2
  // TODO: Task 3

  private readonly formbuilder = inject(FormBuilder);
  private readonly uploadSvc = inject(UploadService);
  private readonly router = inject(Router);

  form !: FormGroup;
  public webcamImage!: WebcamImage;

  ngOnInit(): void {
    this.webcamImage = this.uploadSvc.webcamImage;

    this.form = this.formbuilder.group({
      title : this.formbuilder.control("", [Validators.required, Validators.minLength(5)]),
      comments : this.formbuilder.control(""),      
    })
  }

  post() {
    const formData = new FormData();
    formData.set("title", this.form.get('title')?.value);
    formData.set('comments', this.form.get('comments')?.value);

    const blob = dataToImage(this.webcamImage.imageAsDataUrl);
    formData.set('picture', blob);

    const date = new Date();
    formData.set('date', date.getTime().toString());
    // const post : Post = {
    //   title : this.form.get('title')?.value,
    //   comments : this.form.get('comments')?.value,
    //   date : date
    // }
    // formData.set("post", JSON.stringify(post));

    this.uploadSvc.upload(formData)
      .then(resp => {
        console.log(resp);
        
        this.router.navigate(['/']);
       })
      .catch(err => {
        console.error(err);
        alert(JSON.stringify(err.error));
      });
  }

  BacktoMain() {
    if(confirm("Are you sure you want to discard the picture?")){
      this.router.navigate(['/']);
    }
  }

}
