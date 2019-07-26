import { Component, OnInit } from '@angular/core';
import { UserService} from "./../services/user.service";
import { User } from "./../models/user.model";
import { from } from 'rxjs';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
