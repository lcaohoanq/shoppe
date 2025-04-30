import {config} from "../../env/env.config";

export const getAvatarUrl = (text: string) => {
  return `${config.url.AVATARS_DICEBEAR_URL}/avataaars/svg?seed=${text}`
}

export const isAdmin = (keycloak: { tokenParsed: { resource_access: { [x: string]: { roles: string | string[]; }; }; }; }) => {
  return keycloak?.tokenParsed?.resource_access?.['movies-app']?.roles?.includes('MOVIES_ADMIN') ?? false
}

export const handleLogError = (error: { response: { data: any; }; request: any; message: any; }) => {
  if (error.response) {
    console.log(error.response.data)
  } else if (error.request) {
    console.log(error.request)
  } else {
    console.log(error.message)
  }
}
