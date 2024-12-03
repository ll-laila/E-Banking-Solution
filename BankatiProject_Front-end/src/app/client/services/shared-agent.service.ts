import { Injectable } from '@angular/core';
import {Agent} from "../models/agent";

@Injectable({
  providedIn: 'root'
})
export class SharedAgentService {
  private agent: Agent;
  constructor() { }

  setAgent(agent: Agent) {
    this.agent = agent;
  }

  getAgent(): Agent {
    return this.agent;
  }
}
