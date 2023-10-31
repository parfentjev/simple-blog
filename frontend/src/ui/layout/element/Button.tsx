import { FC } from 'react'
import styles from './Element.module.css'

const Button: FC<{
  text: string
  onClick?: Function
  type?: 'submit' | 'reset' | 'button'
}> = ({ text, onClick = () => {}, type = 'button' }) => {
  return (
    <button
      type={type}
      onClick={() => onClick()}
      className={styles.primary_button}
    >
      {text}
    </button>
  )
}

export default Button
