import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiRole } from '@data/api/api-role';
import { Role } from '@data/model/role.model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class RoleDataService {
  constructor(private http: HttpClient) {}

  getRoles(): Observable<any> {
    const url = `${environment.apiUrl}/role`;
    return this.http.get<ApiRole[]>(url).pipe(map((response) => response.map((r) => Role.adapt(r))));
  }
}
