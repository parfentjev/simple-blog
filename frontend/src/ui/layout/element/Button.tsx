import { FC } from 'react'
import styles from './Element.module.css'

export enum ButtonStyle {
  Primary,
  Secondary,
}

const Button: FC<{
  text: string
  onClick?: Function
  type?: 'submit' | 'reset' | 'button'
  style?: ButtonStyle
}> = ({ text, onClick = () => {}, type = 'button', style }) => {
  let className

  switch (style) {
    case ButtonStyle.Secondary: {
      className = styles.secondary_button
      break
    }
    default: {
      className = styles.primary_button
      break
    }
  }

  return (
    <button
      type={type}
      onClick={() => onClick()}
      className={styles.button + ` ` + className}
    >
      {text}
    </button>
  )
}

export default Button
