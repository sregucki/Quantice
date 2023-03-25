import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authApiUrl: string;

  constructor(private http: HttpClient) {
    this.authApiUrl = environment.authApiUrl;
  }

  login(email: string, password: string): void {
    this.http.post<object>(`${this.authApiUrl}/login`, { email, password}, {observe: 'response'})
    .subscribe((response: any) => { //TODO handle error when no token
      document.cookie = '';
      document.cookie = 'quantice-' + response.body.token;
      this.redirectToDashboard();
    });
  }

  register(username: string, password: string, email: string) {
    this.http.post(`${this.authApiUrl}/register`, {username, password, email}).subscribe();
  }

  redirectToDashboard() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', environment.dashboardUrl + '/dashboard', true);
    xhr.setRequestHeader('Access-Control-Allow-Origin', '*');
    xhr.withCredentials = true;
    xhr.send();
    window.location.href = environment.dashboardUrl + '/dashboard';
  }

}
