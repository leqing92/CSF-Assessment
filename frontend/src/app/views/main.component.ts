import { Component, Output, inject } from '@angular/core';
import { UploadService } from '../upload.service';
import { WebcamImage, WebcamInitError, WebcamUtil } from 'ngx-webcam';
import { Observable, Subject } from 'rxjs';
import { aspectRatio } from '../models';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent {

  // TODO: Task 1
  private readonly uploadSvc = inject(UploadService);
  private readonly router = inject(Router);

  public messages: any[] = [];
  // latest snapshot
  public webcamImage!: WebcamImage;
  // webcam snapshot trigger
  private trigger: Subject<void> = new Subject<void>();  

  width : number = 500;
  height : number = 282;
  imageType: string ="image/png";
  aspectRatio : aspectRatio[] = [
    {
      aspect : "16:9",
      width : 500,
      height : 282
    },
    {
      aspect : "4:3",
      width : 500,
      height : 375
    },
    {
      aspect : "3:2",
      width : 500,
      height : 333
    },
    {
      aspect : "1:1",
      width : 500,
      height : 500
    },
  ]

  public ngOnInit(): void {
    
  }

  public triggerSnapshot(): void {
    this.trigger.next();
    this.router.navigate(['/view2']);
  }

  public handleImage(webcamImage: WebcamImage): void {
    this.addMessage('Received webcam image');
    console.log(webcamImage);
    this.webcamImage = webcamImage;

    this.uploadSvc.webcamImage = webcamImage;
  }

  addMessage(message: any): void {
    console.log(message);
    this.messages.unshift(message);
  }

  public get triggerObservable(): Observable<void> {
    return this.trigger.asObservable();
  }

  handleAspectRatio(index: number) {
    console.log(this.aspectRatio[index]);
    this.width = this.aspectRatio[index].width;
    this.height = this.aspectRatio[index].height;
  }

  defaultAspectRatio(index: number) {
    if(index == 0){
      this.width = this.aspectRatio[index].width;
      this.height = this.aspectRatio[index].height;
      
      return true
    }
    return false;
  }
}
