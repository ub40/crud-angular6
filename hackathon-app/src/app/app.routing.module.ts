import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { TitlesComponent } from './titles/titles.component';
import { UsersListComponent } from './users-list/users-list.component';
import { AddUserComponent } from './add-user/add-user.component';


const routes: Routes = [
    { path: 'titles', component: TitlesComponent },
    { path: 'users', component: UsersListComponent},
    { path: 'add', component: AddUserComponent},
];


@NgModule({
    imports: [
      RouterModule.forRoot(routes)
    ],
    exports: [
      RouterModule
    ],
    declarations: []
  })
  export class AppRoutingModule { }
