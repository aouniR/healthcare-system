import { RouterModule, Routes } from '@angular/router';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { LandingPageComponent } from './pages/landing-page/components';
import { NgModule } from '@angular/core';
import { LoginComponent } from './pages/login/login.component';
import { AdminComponent } from './layout/components';
import { AdminAuthGuard } from './shared/services/admin-auth-guard.service';
import { AgentAuthGuard } from './shared/services/agent-auth-guard.service';
import { AgentsLayoutComponent } from './layout/components/agents/agents-layout.component';
import { ProfSanteAuthGuard } from './shared/services/profSante-auth-guard.service';

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
        {
          path: 'schemas/addSchema',
          loadComponent: () => import('./pages/add-schema/add-schema.component')
        },
        {
          path: 'schemas/listAll',
          loadComponent: () => import('./pages/list-schemas/list-schemas.component')
        }
      ]
    }, 
    { 
      path: 'profSante',
      component: AgentsLayoutComponent,
      canActivate: [ProfSanteAuthGuard],
      children: [
        {
          path: '',
          redirectTo: 'patients',
          pathMatch: 'full'
        },
        {
          path: 'patients',
          loadComponent: () => import('./pages/patients/patients.component')
        },
        {
          path: 'patients/treatment/:id',
          loadComponent: () => import('./pages/treatment/treatment.component')
        },
        {
          path: 'patients/updateTreatment/:id',
          loadComponent: () => import('./pages/update-treatment/update-treatment.component')
        },
        {
          path: 'settings',
          loadComponent: () => import('./pages/settings/settings.component')
        }
      ]
    },
    { 
      path: 'agent',
      component: AgentsLayoutComponent,
      canActivate: [AgentAuthGuard],
      children: [
        {
          path: '',
          redirectTo: 'patients',
          pathMatch: 'full'
        },
        {
          path: 'patients',
          loadComponent: () => import('./pages/patients/patients.component')
        },
        {
          path: 'patients/:id',
          loadComponent: () => import('./pages/update-record/update-record.component')
        },
        {
          path: 'patients/treatment/:id',
          loadComponent: () => import('./pages/treatment/treatment.component')
        },
        {
          path: 'patients/monitoring/:id',
          loadComponent: () => import('./pages/monitoring/monitoring.component')
        },
        {
          path: 'patients/updateMonitoring/:id',
          loadComponent: () => import('./pages/update-monitoring/update-monitoring.component')
        },
        {
          path: 'settings',
          loadComponent: () => import('./pages/settings/settings.component')
        }
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
