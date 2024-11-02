import { RouterModule, Routes } from '@angular/router';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { LandingPageComponent } from './pages/landing-page/components';
import { NgModule } from '@angular/core';
import { LoginComponent } from './shared/components/login/login.component';
import { AdminComponent } from './layout/components';
import { AdminAuthGuard } from './shared/services/admin-auth-guard.service';

const routes: Routes = [
    {path: '', component: LandingPageComponent},
    {path: 'login', component: LoginComponent},
    { 
      path: 'admin',
      component: AdminComponent,
      canActivate: [AdminAuthGuard],
      children: [
        {
          path: '',
          redirectTo: 'dashboard',
          pathMatch: 'full'
        },
        {
          path: 'dashboard',
          loadComponent: () => import('./pages/dashboard-page/dashboard-page.component')
        },
        {
          path: 'users/addUser',
          loadComponent: () => import('./pages/add-user/add-user.component')
        },
        {
          path: 'users/updateUser/:id',
          loadComponent: () => import('./pages/update-user/update-user.component')
        },
        {
          path: 'users/listUsers',
          loadComponent: () => import('./pages/list-users/list-users.component')
        },
        {
          path: 'metaModel/addMetaModel',
          loadComponent: () => import('./pages/add-meta-model/add-meta-model.component')
        },
        {
          path: 'metaModel/addFields/:id',
          loadComponent: () => import('./pages/add-fields-meta-model/add-fields-meta-model.component')
        },
        {
          path: 'metaModel/listAll',
          loadComponent: () => import('./pages/list-meta-models/list-meta-models.component')
        },
        {
          path: 'settings/:id',
          loadComponent: () => import('./pages/settings/settings.component')
        },
        {
          path: 'notifications',
          loadComponent: () => import('./pages/list-notifications/list-notifications.component')
        },
        {
          path: 'notifications/:id',
          loadComponent: () => import('./pages/notification/notification.component')
        },
      ]
    }, 
    {path: '404', component: NotFoundComponent},
    {path: '**', redirectTo: '404'}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule { }
