import { Injectable } from '@angular/core';
import {IAgent} from '../models/Agent';

@Injectable({
  providedIn: 'root'
})
export class SharedAgentService {

  private agent: IAgent;
  constructor() { }

  setAgent(agent: IAgent) {
    this.agent = agent;
  }

  getAgent(): IAgent {
    return this.agent;
  }
}
