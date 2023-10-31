import { FC } from 'react'
import styles from './LoadMoreButton.module.css'

const LoadMoreButton: FC<{ onClick: Function }> = ({ onClick }) => {
  return (
    <div className={styles.container}>
      <button onClick={() => onClick()}>Load more!</button>
    </div>
  )
}

export default LoadMoreButton
