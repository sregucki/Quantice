import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../service/auth-service/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginForm: FormGroup;

  constructor(private authService: AuthService, fb: FormBuilder) {
    this.loginForm = fb.group({
        email: [null, [Validators.required]],
        password: [null, [Validators.required]]
      },
      {
        updateOn: 'submit'
      });
  }

  onSubmit(): void {
    this.authService.login(this.loginForm.value['email'], this.loginForm.value['password']);
  }

  redirectOAuth2(): void {
    window.location.href = 'http://localhost:8090/oauth2/authorize/google';
  }

}
