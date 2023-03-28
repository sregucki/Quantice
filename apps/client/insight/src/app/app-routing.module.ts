import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DashboardComponent} from "./component/dashboard/dashboard.component";
import {StockComponent} from "./component/stock/stock.component";
import {AuthService} from "./service/auth-service/auth.service";
import {Oauth2Service} from "./service/oauth2-service/oauth2.service";

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthService]},
  { path: 'stock/:name', component: StockComponent },
  { path: 'oauth2/:token', canActivate: [Oauth2Service], component: DashboardComponent},
  { path: '**', component: DashboardComponent} // TODO redirect to some page
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
