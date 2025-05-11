import { Box, IconButton, Modal } from '@mui/material'
import CloseIcon from '@mui/icons-material/Close'
import { styles } from './style'

interface BannerModalProps {
  open: boolean
  onClose: () => void
  bannerSrc: string
}

export default function BannerModal({ open, onClose, bannerSrc }: BannerModalProps) {
  return (
    <Modal
      open={open}
      onClose={onClose}
      aria-labelledby='movie-preview-modal'
      aria-describedby='movie-banner-preview'
      sx={styles.modal}
    >
      <Box sx={styles.modalBox}>
        <IconButton onClick={onClose} sx={styles.closeButton}>
          <CloseIcon />
        </IconButton>
        {bannerSrc && (
          <img src={bannerSrc} alt='Movie Banner' loading='lazy' style={styles.bannerImage as React.CSSProperties} />
        )}
      </Box>
    </Modal>
  )
}
