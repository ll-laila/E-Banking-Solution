import { Injectable } from '@angular/core';
import {ServiceAgent} from "../models/serviceAgent";

@Injectable({
  providedIn: 'root'
})
export class SharedAgentServiceService {

  private serviceAgent: ServiceAgent;
  constructor() { }

  setServiceAgent(serviceAgent: ServiceAgent) {
    this.serviceAgent = serviceAgent;
  }

  getServiceAgent(): ServiceAgent {
    return this.serviceAgent;
  }
}
