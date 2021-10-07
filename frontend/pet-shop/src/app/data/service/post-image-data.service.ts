import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class PostImageDataService {
  constructor(private http: HttpClient) {}
}
