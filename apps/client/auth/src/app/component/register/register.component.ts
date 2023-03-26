import {Component} from '@angular/core';
import {AuthService} from "../../service/auth-service/auth.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  registerForm: FormGroup;

  constructor(private authService: AuthService, fb: FormBuilder) {
    this.registerForm = fb.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]],
      email: [null, [Validators.required]]
    },
      {
      updateOn: 'submit'
    });
  }

  onSubmit(): void {
    console.log(this.registerForm.value['username'], this.registerForm.value['password'], this.registerForm.value['email'])
    this.authService.register(this.registerForm.value['username'], this.registerForm.value['password'], this.registerForm.value['email']);
  }

}
