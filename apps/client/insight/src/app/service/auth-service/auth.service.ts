import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from "@angular/router";
import {map, Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthService implements CanActivate {

  constructor(private http: HttpClient) { }

  getTokenFromCookie(): string | null {
    const tokenPrefix = 'quantice-';
    const decodedCookie = decodeURIComponent(document.cookie);
    let cookie = decodedCookie.split(';').filter(cookie => cookie.includes(tokenPrefix));
    let token = cookie.length != 0 ? cookie[0].substring(tokenPrefix.length) : null;
    if (token) {
      return token[0] == '-' ? token.substring(1) : token;
    }
    return token;
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (!environment.isAuthEnabled) {
      return true;
    }
    if (this.getTokenFromCookie()) {
      return this.http.post(environment.authServerUrl, {token: this.getTokenFromCookie()}).pipe(
        map((response: any) => {
          if (response.valueOf()) {
            return true;
          }
          AuthService.redirectToLoginPage();
          return false;
        })
      );
    }
    else {
      AuthService.redirectToLoginPage();
      return false;
    }
  }

  public static redirectToLoginPage(): void {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', environment.loginPageUrl, true);
    xhr.withCredentials = true;
    xhr.send();
    window.location.href = environment.loginPageUrl;
  }

}
