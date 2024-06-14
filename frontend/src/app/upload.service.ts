import { HttpClient, HttpHeaders } from "@angular/common/http";
import { inject } from "@angular/core";
import { WebcamImage } from "ngx-webcam";
import { Observable, Subject, firstValueFrom } from "rxjs";

export class UploadService {

  // TODO: Task 3.
  // You may add add parameters to the method
  private readonly http = inject(HttpClient);

  public webcamImage!: WebcamImage; 

  upload(formdata : FormData) {
    // const headers = new HttpHeaders()
    //   .set('Content-Type', "Multipart/form-data");

    return firstValueFrom(this.http.post("/api/image/upload", formdata));
  }

}
