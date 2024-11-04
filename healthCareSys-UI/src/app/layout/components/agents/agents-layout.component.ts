import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../shared/services/auth.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { agentService } from '../../../shared/services/agent.service';


@Component({
  selector: 'app-agents-layout',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './agents-layout.component.html',
  styleUrl: './agents-layout.component.scss'
})
export class AgentsLayoutComponent implements OnInit{
  isPorfSante: boolean = false;
  isAgent: boolean = false; 

  constructor(private authService: AuthService, 
              private router: Router,
              private agSr: agentService) {}

  ngOnInit(): void {
    this.isPorfSante = this.authService.isPorfSante();
    this.isAgent = this.authService.isAgent(); 
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
  async addNewPatient(){
    const patientData= await this.agSr.createRecord();
    if(patientData.id)
       this.router.navigate([`/agent/patients/${patientData.id}`]);
  }
}
