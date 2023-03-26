import {Injectable} from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree
} from "@angular/router";
import {map, Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../auth-service/auth.service";

@Injectable({
  providedIn: 'root'
})
export class Oauth2Service implements CanActivate {

  constructor(private http: HttpClient, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (!environment.isAuthEnabled) {
      return true;
    }
    if (state.url.includes('oauth2')) {
      const token = state.url.split('/')[2];
      return this.http.post(environment.authServerUrl, {token: token}).pipe(
        map((response: any) => {
          document.cookie = `quantice-${token}`;
          if (response.valueOf()) {
            this.router.navigate(['/dashboard']).then();
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
}
