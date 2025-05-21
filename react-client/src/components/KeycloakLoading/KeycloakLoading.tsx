import { CircularProgress, Typography, Box } from '@mui/material'

const KeycloakLoading: React.FC = () => {
  return (
    <Box display='flex' flexDirection='column' alignItems='center' justifyContent='center' height='100vh'>
      <CircularProgress color='primary' size={60} thickness={4} />
    </Box>
  )
}

export default KeycloakLoading
