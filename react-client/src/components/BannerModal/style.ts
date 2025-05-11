export const styles = {
  modal: {
    '& .MuiBox-root': {
      width: { xs: '95%', sm: '80%' },
      maxWidth: '1000px'
    }
  },
  modalBox: {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: '80%',
    maxWidth: '1000px',
    bgcolor: 'rgba(0, 0, 0, 0.9)',
    boxShadow: 24,
    outline: 'none',
    borderRadius: 2
  },
  closeButton: {
    position: 'absolute',
    right: 8,
    top: 8,
    color: 'white',
    bgcolor: 'rgba(0, 0, 0, 0.5)',
    '&:hover': {
      bgcolor: 'rgba(131, 75, 255, 0.7)'
    },
    zIndex: 1
  },
  bannerImage: {
    width: '100%',
    height: 'auto',
    maxHeight: '80vh',
    objectFit: 'cover',
    borderRadius: '8px'
  }
}
