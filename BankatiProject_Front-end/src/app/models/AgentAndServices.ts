import {AgentRequest} from "./AgentRequest";
import {AgentService} from "../service/agent.service";
import {IAgentServices} from "./AgentServices";

export interface AgentAndServices {
 agent:AgentRequest;
 services:IAgentServices[];
}



