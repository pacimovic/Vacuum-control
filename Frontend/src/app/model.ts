export interface LoginResponse {
    jwt: string,
    permission: {
        can_create_users: boolean,
        can_read_users: boolean,
        can_update_users: boolean,
        can_delete_users: boolean
    }
}

export interface LoginRequest {
    email: string,
    password: string
}

export interface User {
    userId: number,
    name: string,
    surname: string,
    email: string,
    password: string,
    permission: {
        can_create_users: boolean,
        can_read_users: boolean,
        can_update_users: boolean,
        can_delete_users: boolean
    }
}

export interface Permission {
    can_create_users: boolean,
    can_read_users: boolean,
    can_update_users: boolean,
    can_delete_users: boolean
}