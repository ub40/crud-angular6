import { Component, OnInit } from '@angular/core';
import { UserService} from './../services/user.service';
import { User } from './../models/user.model';


@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {

  user: User = new User();
  submitted = false;

  constructor(private userService: UserService) { }

  ngOnInit() {
  }

  newUser(): void {
    this.submitted = false;
    this.user = new User;
  }

  save() {
    this.userService.createUser(this.user)
    .subscribe(data => console.log(data, error => console.log(error)));
    this.user = new User();
  }

  onSubmit() {
    this.submitted = true;
    this.save();
  }
}
