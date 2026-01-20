export interface UserData {
    userID: string;
    email: string;
    name: string;
    role: 'ADMIN' | 'DEVELOPER' | 'TESTER';
}
