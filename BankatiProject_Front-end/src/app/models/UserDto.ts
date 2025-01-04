// user-dto.model.ts
export interface UserDto {
    id: string;
    agentId: string;
    firstName: string;
    lastName: string;
    email: string;
    address: string;
    cin: string;
    birthDate: Date;
    phoneNumber: string;
    role: string; // Vous pouvez soit utiliser `string` pour le rôle ou un modèle de rôle si vous en avez un
    password: string;
    isFirstLogin: boolean;
    createdDate: Date;
    commercialRn: string;
    image: string;
    patentNumber: string;
    isPaymentAccountActivated: boolean;
    typeHissab: string;
    currency: string;
    token: string;
    message: string;
}
