import { Component, OnInit } from '@angular/core';
import { SharedModule } from '../../shared/shared.module'
import { CommonModule } from '@angular/common';
import { BarChartComponent } from './bar-chart/bar-chart.component';
import { adminDashboardService } from '../../shared/services/adminDashboard.service';

@Component({
  selector: 'app-dashboard-page',
  standalone: true,
  imports: [CommonModule, SharedModule, BarChartComponent],
  templateUrl: './dashboard-page.component.html',
  styleUrls: ['./dashboard-page.component.scss']
})
export default class DashboardPageComponent implements OnInit  {

  totalAgents: number | null = null; 
  totalMetaMedicalRecords: number | null = null; 
  totalMetaMedicalProcedures: number | null = null; 
  totalMetaMedicalMonitoring: number | null = null; 

  totalMedicalRecords: number | null = null; 
  totalMedicalProcedures: number | null = null; 
  totalMedicalMonitoring: number | null = null; 

  constructor(private dashService: adminDashboardService) {}

  ngOnInit(): void {
    this.fetchTotalAgents();
    this.fetchMetaMedicalRecordsCount();
    this.fetchMetaMedicalMonitoringCount();
    this.fetchMetaMedicalProceduresCount();
    this.fetchMedicalRecordsCount();
    this.fetchMedicalProceduresCount();
    this.fetchMedicalMonitoringCount();
  }

  private async fetchTotalAgents() {
    this.totalAgents = await this.dashService.updateAgentCount();
  }

  private async fetchMetaMedicalRecordsCount() {
    const updateMetaMedicalRecordCountJson=  await this.dashService.updateMetaMedicalRecordCount();
    this.totalMetaMedicalRecords = updateMetaMedicalRecordCountJson.length;
  }

  private async fetchMedicalRecordsCount() {
    const updateMedicalRecordCountJson = await this.dashService.updateMedicalRecordCount();
    this.totalMedicalRecords = updateMedicalRecordCountJson.length;
  }
  
  private async fetchMetaMedicalProceduresCount() {
    const MetaMedicalProcedureCountJson = await this.dashService.updateMetaMedicalProcedureCount();
    this.totalMetaMedicalProcedures = MetaMedicalProcedureCountJson.length;
  }

  private async fetchMedicalProceduresCount() {
    const MedicalProcedureCountJson = await this.dashService.updateMedicalProcedureCount();
    this.totalMedicalProcedures = MedicalProcedureCountJson.length;
  }

  private async fetchMetaMedicalMonitoringCount() {
    const metaMedicalMonitoringCountJson = await this.dashService.updateMetaMedicalMonitoringCount();
    this.totalMetaMedicalMonitoring = metaMedicalMonitoringCountJson.length;
  }

  private async fetchMedicalMonitoringCount() {
    const updateMedicalMonitoringCountJson = await this.dashService.updateMedicalMonitoringCount();
    this.totalMedicalMonitoring = updateMedicalMonitoringCountJson.length;
  }
}
