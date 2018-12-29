# NullPath

Deviation(s) from the UML (James):
- Dx, Dy, Height, Width, Radius variables used for items and characters (excluding radius for character), where individual components may be set for 0 for something like the terrain modifiers
- ProjectileLauncher can be customized for varying launchers - projectile type and motion is specified upon construction, so no inherited classes are used
- Arc object is integrated within the move function, and a boolean inArc is required for characters and items. 

^May be further customized
