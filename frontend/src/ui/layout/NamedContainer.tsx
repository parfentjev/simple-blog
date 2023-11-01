import { FC, ReactNode } from 'react'

const NamedContainer: FC<{
  children: ReactNode
  name?: string
  centered?: boolean
  className?: string
}> = ({ children, name, centered, className }) => {
  return (
    <div
      className={
        (className ? className : ``) + ` ` + (centered ? `text_centered` : ``)
      }
    >
      {name && <h1>{name}</h1>}
      <div>{children}</div>
    </div>
  )
}

export default NamedContainer
