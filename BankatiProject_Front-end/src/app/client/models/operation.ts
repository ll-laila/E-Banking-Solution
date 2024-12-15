export interface Operation {
    id: number;
    ref:  string;
    creditorName: string;
    clientName: string;
    serviceName: string;
    amount: number;
    date: Date;
  }
