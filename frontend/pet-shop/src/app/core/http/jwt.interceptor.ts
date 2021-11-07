import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '@app/service/authentication.service';
import { GlobalService } from '@app/service/global.service';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(
    private authenticationService: AuthenticationService,
    private router: Router,
    private globalService: GlobalService
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    // add auth header with jwt if user is logged in and request is to the api url
    const jwt = this.globalService.credentials?.jwt;
    const isApiUrl = request.url.startsWith(environment.apiUrl);

    if (jwt && isApiUrl) {
      request = request.clone({
        headers: request.headers.set('Authorization', 'Bearer ' + jwt),
      });
    }

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          console.log('Error 401 -->', error);
          this.authenticationService.unauthorizedAccess();
          // this.router.navigate(['/auth/login']);
        }
        if (error.status === 404) {
          console.log('Error 404 -->', error);
          this.router.navigate(['/404']);
        }
        if (error.status === 500) {
          console.log('Error 500 -->', error);
          this.router.navigate(['/500']);
        }
        return throwError(error);
      })
    );
  }
}
