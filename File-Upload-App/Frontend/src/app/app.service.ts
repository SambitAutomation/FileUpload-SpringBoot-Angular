import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
    providedIn:"root"
})

export class AppService{

    constructor(private http:HttpClient){}

    saveImagefile(body:any){
        return this.http.post<any>(`http://localhost:1010/addFile`, body);
    }
}