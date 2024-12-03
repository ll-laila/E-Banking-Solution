export interface MyToken {
  id : number;
  role: string;
  sub: string;
  iat: number;
  exp: number;
  isFirstLogin : boolean;
}
