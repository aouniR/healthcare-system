import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class adminDashboardService {

  async updateAgentCount() {
    try {
      const response = await fetch('http://localhost:8080/users/getAllUsers', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer '+localStorage.getItem('authToken')
        }
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const users = await response.json();
      const agentCount = users.length; 
      return agentCount;
    } catch (error) {
      console.error('Error fetching agent count:', error);
    }
  }

  async updateMetaMedicalRecordCount() {
    try {
      const response = await fetch('http://localhost:8080/metamodels/getMetaModelByType/DOSSIER_MEDICAL', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer '+localStorage.getItem('authToken')
        }
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      return await response.json();
    } catch (error) {
      console.error('Error fetching agent count:', error);
    }
  }

  async updateMedicalRecordCount() {
    try {
      const response = await fetch('http://localhost:8080/medicalrecords/getAllMedicalRecord', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer '+localStorage.getItem('authToken')
        }
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      return await response.json();
    } catch (error) {
      console.error('Error fetching agent count:', error);
    }
  }


  async updateMedicalRecordCountPerMonth(year :number) {
    try {
      const response = await fetch(`http://localhost:8080/medicalrecords/getNumberMedicalRecordByMonth/${year}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer '+localStorage.getItem('authToken')
        }
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      return await response.json();
    } catch (error) {
      console.error('Error fetching agent count:', error);
    }
  }

  async updateMetaMedicalMonitoringCount() {
    try {
      const response = await fetch('http://localhost:8080/metamodels/getMetaModelByType/SUIVI', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer '+localStorage.getItem('authToken')
        }
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      return await response.json();
    } catch (error) {
      console.error('Error fetching agent count:', error);
    }
  }

  async updateMedicalMonitoringCount() {
    try {
      const response = await fetch('http://localhost:8080/medicalrecords/getAllMedicalMonitoring', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer '+localStorage.getItem('authToken')
        }
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      return await response.json();

    } catch (error) {
      console.error('Error fetching agent count:', error);
    }
  }

  async updateMedicalMonitoringCountPerMonth(year :number) {
    try {
      const response = await fetch(`http://localhost:8080/medicalrecords/getNumberMedicalMonitoringByMonth/${year}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer '+localStorage.getItem('authToken')
        }
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      return await response.json();
    } catch (error) {
      console.error('Error fetching agent count:', error);
    }
  }

  async updateMetaMedicalProcedureCount() {
    try {
      const response = await fetch('http://localhost:8080/metamodels/getMetaModelByType/ACTE_MEDICAL', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer '+localStorage.getItem('authToken')
        }
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      return await response.json();
    } catch (error) {
      console.error('Error fetching agent count:', error);
    }
  }

  async updateMedicalProcedureCount() {
    try {
      const response = await fetch('http://localhost:8080/medicalrecords/getAllMedicalProcedure', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer '+localStorage.getItem('authToken')
        }
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      return await response.json();
    } catch (error) {
      console.error('Error fetching agent count:', error);
    }
  }

  async updateMedicalProcedureCountPerMonth(year :number) {
    try {
      const response = await fetch(`http://localhost:8080/medicalrecords/getNumberMedicalProcedureByMonth/${year}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer '+localStorage.getItem('authToken')
        }
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      return await response.json();
    } catch (error) {
      console.error('Error fetching agent count:', error);
    }
  }

  async createMedicalRecordSchema(request: any) {
    try {
      const response = await fetch(`http://localhost:8080/medicalrecords/saveMedicalRecordSchema`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer '+localStorage.getItem('authToken'),
        },
        body: JSON.stringify(request) 
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return await response.status;
    } catch (error) {
      console.error('Error fetching agent count:', error);
      return null;
    }
  }

  async getMedicalRecordSchema() {
    try {
      const response = await fetch(`http://localhost:8080/medicalrecords/getMedicalRecordSchema`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer '+localStorage.getItem('authToken')
        }
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return await response.json();
    } catch (error) {
      console.error('Error fetching agent count:', error);
      return null;
    }
  }

  async createMedicalProcedureSchema(request: any) {
    try {
      const response = await fetch(`http://localhost:8080/medicalrecords/saveMedicalProcedureSchema`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer '+localStorage.getItem('authToken'),
        },
        body: JSON.stringify(request) 
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return await response.status;
    } catch (error) {
      console.error('Error fetching agent count:', error);
      return null;
    }
  }

  async getMedicalProcedureSchema() {
    try {
      const response = await fetch(`http://localhost:8080/medicalrecords/getMedicalProcedureSchema`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer '+localStorage.getItem('authToken')
        }
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return await response.json();
    } catch (error) {
      console.error('Error fetching agent count:', error);
      return null;
    }
  }

  async createMedicalMonitoringSchema(request: any) {
    try {
      const response = await fetch(`http://localhost:8080/medicalrecords/saveMedicalMonitoringSchema`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer '+localStorage.getItem('authToken'),
        },
        body: JSON.stringify(request) 
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return await response.status;
    } catch (error) {
      console.error('Error fetching agent count:', error);
      return null;
    }
  }

  async getMedicalMonitoringSchema() {
    try {
      const response = await fetch(`http://localhost:8080/medicalrecords/getMedicalMonitoringSchema`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer '+localStorage.getItem('authToken')
        }
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return await response.json();
    } catch (error) {
      console.error('Error fetching agent count:', error);
      return null;
    }
  }
}

