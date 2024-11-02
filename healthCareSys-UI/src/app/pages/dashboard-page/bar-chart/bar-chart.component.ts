import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';

// third party
import {
  NgApexchartsModule,
  ApexChart,
  ChartComponent,
  ApexDataLabels,
  ApexAxisChartSeries,
  ApexXAxis,
  ApexYAxis,
  ApexTooltip,
  ApexPlotOptions,
  ApexResponsive
} from 'ng-apexcharts';

import { CommonModule } from '@angular/common';
import { adminDashboardService } from '../../../shared/services/adminDashboard.service';
import { MetaModelsService } from '../../../shared/services/metaModels.service';

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  dataLabels: ApexDataLabels;
  plotOptions: ApexPlotOptions;
  responsive: ApexResponsive[];
  xaxis: ApexXAxis;
  colors: string[];
  yaxis: ApexYAxis;
  tooltip: ApexTooltip;
};

@Component({
  selector: 'app-bar-chart',
  standalone: true,
  imports: [CommonModule, NgApexchartsModule, SharedModule],
  templateUrl: './bar-chart.component.html',
  styleUrl: './bar-chart.component.scss'
})
export class BarChartComponent  implements OnInit {
  @Input() totalAgents: number | null = null;
  @Input() totalPatients: number | null = null; 
  @ViewChild('chart') chart!: ChartComponent;
  chartOptions!: Partial<ChartOptions>;

  constructor(private dashService: adminDashboardService, 
              private metaModelsService: MetaModelsService
             ) {
    this.chartOptions = {
      series: [
        {
          name: 'MetaModels',
          data: []
        },
        {
          name: 'Records',
          data: []
        },
        {
          name: 'Procedure',
          data: []
        },
        {
          name: 'Monitoring',
          data: []
        }
      ],
      dataLabels: {
        enabled: false
      },
      chart: {
        type: 'bar',
        height: 480,
        stacked: true,
        toolbar: {
          show: true
        },
        background: 'transparent'
      },
      colors: ['#d3eafd', '#2196f3', '#673ab7', '#ede7f6'],
      responsive: [
        {
          breakpoint: 480,
          options: {
            legend: {
              position: 'bottom',
              offsetX: -10,
              offsetY: 0
            }
          }
        }
      ],
      plotOptions: {
        bar: {
          horizontal: false,
          columnWidth: '50%'
        }
      },
      xaxis: {
        type: 'category',
        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
      },
      tooltip: {
        theme: 'light'
      }
    };
  }


  ngOnInit(): void {
    this.fetchRecords();
    this.fetchProcedure();
    this.fetchMonitoring();
    this.fetchMetaModels();
  }

  private async fetchRecords() {
      const recordsData = await this.dashService.updateMedicalRecordCountPerMonth(2024);
  const recordsSeries = this.chartOptions.series?.find(x => x.name === 'Records');
  
  if (recordsSeries) {
    recordsSeries.data = recordsData; 
  }
  }

  private async fetchProcedure() {
    const proceduresData = await this.dashService.updateMedicalProcedureCountPerMonth(2024);
    const proceduresSeries = this.chartOptions.series?.find(x => x.name === 'Procedure');

    if (proceduresSeries) {
      proceduresSeries.data = proceduresData; 
    }
  }

  private async fetchMonitoring() {
    const monitoringData = await this.dashService.updateMedicalMonitoringCountPerMonth(2024);
    const monitoringSeries = this.chartOptions.series?.find(x => x.name === 'Monitoring');

    if (monitoringSeries) {
      monitoringSeries.data = monitoringData; 
    }
  }

  private async fetchMetaModels() {
    const metaModelData = await this.metaModelsService.updateMetaModelCountPerMonth(2024);
    const metModelSeries = this.chartOptions.series?.find(x => x.name === 'MetaModels');

    if (metModelSeries) {
      metModelSeries.data = metaModelData; 
    }
  }

}
