# NullPath

Deviation(s) from the UML (James):
- Dx, Dy, Height, Width, Radius variables used for items and characters (excluding radius for character), where individual components may be set for 0 for something like the terrain modifiers
- ProjectileLauncher can be customized for varying launchers - projectile type and motion is specified upon construction, so no inherited classes are used
- Arc is generated naturally by gravity, so affectGravity boolean is used to dictate linear or non-linear motion

^May be further customized

Notes: 
- Bomb has no explode method (checked for when stage places the item)
- x and y set to centres of objects
