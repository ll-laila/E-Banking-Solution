import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {AgentService} from "../../../service/agent.service";
import {IAgent} from "../../../models/Agent";
import {AgentRequest} from "../../../models/AgentRequest";
import {AdminService} from "../../../service/admin.service";

@Component({
  selector: 'app-agents',
  templateUrl: './new-agent.component.html',
  styleUrls: ['./new-agent.component.scss']
})
export class NewAgentComponent implements OnInit {
  agents: AgentRequest[] = [];
  errorMessage: string | null = null; // Gestion des erreurs
  successMessage: string | null = null;
  constructor(private router: Router, private adminService: AdminService) { }

  ngOnInit(): void {
    this.getAllAgents();
  }


  getAllAgents(): void {
    this.adminService.getAllAgents().subscribe(
      (agents) => {
        this.agents = agents; // Assigner les données reçues
      },
      (error) => {
        console.error('Une erreur s\'est produite lors de la récupération des agents :', error);
        this.errorMessage = 'Impossible de récupérer la liste des agents.';
      }
    );
  }
  addAgent() {
    this.router.navigate(['/add-agent']);
  }

  deleteAgent(id: number) {

  }

  viewAgentDetails(id: number) {
    this.router.navigate(['/details-agent', id]);
  }
}
