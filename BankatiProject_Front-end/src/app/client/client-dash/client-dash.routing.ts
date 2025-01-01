import { Routes } from '@angular/router';
import {DashboardClientComponent} from '../clientsPages/dashboardClient/dashboardClient.component';
import {ProfileClientComponent} from '../clientsPages/profileClient/profileClient.component';
import {HistoryComponent} from '../clientsPages/history/history.component';
import {CreditorsListComponent} from '../clientsPages/creditorsList/creditorsList.component';
import {Payment} from '../clientsPages/payment/payment';
import {WalletCryptoComponent} from '../clientsPages/wallet-crypto/wallet-crypto.component';
import {BudgetPersonelComponent} from '../clientsPages/budget-personel/budget-personel.component';
import {TransferToClientComponent} from '../clientsPages/transfer-to-client/transfer-to-client.component';
import {CarteVirtuelleComponent} from '../clientsPages/carte-virtuelle/carte-virtuelle.component';
import {SubscriptionsComponent} from "../clientsPages/subscriptions/subscriptions.component";



export const ClientDashRoutes: Routes = [
    { path: 'accueil',      component: DashboardClientComponent },
    { path: 'agents',         component: CreditorsListComponent},
    { path: 'transferToClient',   component: TransferToClientComponent },
    { path: 'historique',          component: HistoryComponent  },
    { path :'My-Subscriptions', component:SubscriptionsComponent},
    { path: 'carte-virtuelle',          component: CarteVirtuelleComponent  },
    { path: 'crypto',   component: WalletCryptoComponent },
    { path: 'buget-personnel',   component: BudgetPersonelComponent },
    { path: 'mon-compte',   component: ProfileClientComponent },
    { path: 'paiement',           component: Payment },

];
