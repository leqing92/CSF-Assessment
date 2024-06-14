import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { MainComponent } from './views/main.component';
import { PictureComponent } from './views/picture.component';
import { RouterModule, Routes } from '@angular/router';
import { WebcamModule } from 'ngx-webcam';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { UploadService } from './upload.service';
import { leavePicture } from './guard';

const appRoute : Routes = [
  // do not start with a slash "/"
  // empty represent main page
  {path: '', component : MainComponent},
  // mean /cat
  {path:'view2', component: PictureComponent},  
  // ↓ this must be the last route
  // wildcard
  {path:'**', redirectTo: '/', pathMatch:'full'}
];

@NgModule({
  declarations: [
    AppComponent, MainComponent, PictureComponent
  ],
  imports: [
    BrowserModule,
    WebcamModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoute, {useHash: true})
  ],
  providers: [UploadService],
  bootstrap: [AppComponent]
})
export class AppModule { }
