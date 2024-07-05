export interface LoginResponse {
    jwt: string,
    permission: {
        can_create_users: boolean,
        can_read_users: boolean,
        can_update_users: boolean,
        can_delete_users: boolean,
        can_search_vacuum: boolean,
        can_start_vacuum: boolean,
        can_stop_vacuum: boolean,
        can_discharge_vacuum: boolean,
        can_add_vacuum: boolean,
        can_remove_vacuum: boolean
    }
}

export interface LoginRequest {
    email: string,
    password: string
}

export interface Vacuum {
    id: number,
    name: string,
    status: string,
    created: string,
    active: boolean,
}

export interface NewVacuum {
    name: string
}

export interface ScheduleDate {
    second: string,
    minute: string,
    hour: string,
    dayMonth: string,
    month: string,
    dayWeek: string
}

export interface ErrorMessage {
    id: string,
    date: string,
    vacuum: string,
    userId: number,
    operation: string,
    message: string
}

export interface User {
    id: number,
    name: string,
    surname: string,
    email: string,
    password: string,
    permission: Permission
}

export interface Permission {
    can_create_users: boolean,
    can_read_users: boolean,
    can_update_users: boolean,
    can_delete_users: boolean,
    can_search_vacuum: boolean,
    can_start_vacuum: boolean,
    can_stop_vacuum: boolean,
    can_discharge_vacuum: boolean,
    can_add_vacuum: boolean,
    can_remove_vacuum: boolean
}