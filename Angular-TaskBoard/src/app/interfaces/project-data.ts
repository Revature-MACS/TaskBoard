export interface ProjectData {
    projectId: string,
    name: string,
    description?: string,
    owner: {
        email: string
    } 
}
