import { Component } from '@angular/core';
import { AppService } from './app.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'File Upload App';
  fileData: any = "";

  constructor(private service: AppService) { }

// If we are handling file upload using RequestBody In SpringBoot
  saveImageFile() {
    let body: any = {};
    body.imageData = this.fileData;
    this.service.saveImagefile(body).subscribe((resp) => {
      console.log(resp);
    }, (error) => {
      console.log(error);
    })
  }


  loadImage($event: any) {
    let fileInput = $event.target as HTMLInputElement;

    if (fileInput.files && fileInput.files.length > 0) {

      let file: File = fileInput.files[0];
      let filereader: FileReader = new FileReader();

      filereader.onloadend = (e) => {
        let image = filereader.result;
        this.fileData = image;
      }
      filereader.readAsDataURL(file);
    }
  }


  //If we are handling File upload by using Multipart in SpringBoot
  saveImageFileUsingMultipart() {
    let body = new FormData();
    body.append("file", this.fileData);
    this.service.saveImagefile(body).subscribe((resp) => {
      console.log(resp);
    }, (error: any) => {
      console.log(error);
    })
  }

  encodeImageDataForMultipartFile(event: any) {
    this.fileData = event.target.files[0];
    console.log(this.fileData);

  }

}
