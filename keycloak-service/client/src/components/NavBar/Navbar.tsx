import {useKeycloak} from '@react-keycloak/web'
import {NavLink, useNavigate} from 'react-router-dom'
import {isAdmin} from './Helpers'

function Navbar() {
  const { keycloak } = useKeycloak()
  const navigate = useNavigate()

  const handleLogInOut = () => {
    if (keycloak.authenticated) {
      navigate('/')
      keycloak.logout()
    } else {
      keycloak.login()
    }
  }

  const getUsername = () => keycloak.authenticated && keycloak.tokenParsed?.preferred_username
  const getLogInOutText = () => (keycloak.authenticated ? 'Logout' : 'Login')
  const isAdminUser = keycloak.authenticated && isAdmin(keycloak)

  return (
      <nav className="bg-gray-800 text-white">
        <div className="max-w-7xl mx-auto px-4 py-3 flex items-center justify-between">
          <div className="flex items-center space-x-6">
            <NavLink to="/home" className="text-xl font-bold hover:text-blue-400">Shoppe</NavLink>
            <NavLink to="/home" className={({ isActive }) => isActive ? 'text-blue-400' : 'hover:text-blue-400'}>Home</NavLink>

            {isAdminUser && (
                <div className="relative group">
                  <span className="cursor-pointer hover:text-blue-400">Admin ▾</span>
                  <div className="absolute left-0 mt-2 w-40 bg-white text-black rounded shadow-lg opacity-0 group-hover:opacity-100 transition duration-150 z-50">
                    <NavLink to="/movies" className="block px-4 py-2 hover:bg-gray-100">Movies</NavLink>
                    <NavLink to="/wizard" className="block px-4 py-2 hover:bg-gray-100">Movie Wizard</NavLink>
                  </div>
                </div>
            )}
          </div>

          <div className="flex items-center space-x-4">
            {keycloak.authenticated && (
                <div className="relative group">
                  <div className="flex items-center justify-center gap-2">
                    <img
                      src={`https://avatars.dicebear.com/api/avataaars/${getUsername()}.svg`}
                      alt="Avatar"
                      className="w-8 h-8 rounded-full"
                    />
                    <span className="cursor-pointer hover:text-blue-400">Hi {getUsername()} ▾</span>
                  </div>
                  <div className="absolute right-0 mt-2 w-40 bg-white text-black rounded shadow-lg opacity-0 group-hover:opacity-100 transition duration-150 z-50">
                    <NavLink to="/settings" className="block px-4 py-2 hover:bg-gray-100">Settings</NavLink>
                  </div>
                </div>
            )}
            <button
                onClick={handleLogInOut}
                className="bg-blue-600 hover:bg-blue-700 px-4 py-2 rounded text-sm"
            >
              {getLogInOutText()}
            </button>
          </div>
        </div>
      </nav>
  )
}

export default Navbar
