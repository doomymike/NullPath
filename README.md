# NullPath

Deviation(s) from the UML (James):
- Dx, Dy, Height, Width, Radius variables used for items and characters (excluding radius for character), where individual components may be set for 0 for something like the terrain modifiers
- ProjectileLauncher can be customized for varying launchers - projectile type and motion is specified upon construction, so no inherited classes are used
- Arc is generated naturally by gravity, so affectGravity boolean is used to dictate linear or non-linear motion
- Hazard objects changed to "statuses" held by platforms - should only be added on terrain in the first place, so no point in making an entirely separate object

^May be further customized

Notes: 
- Bomb has no explode method (checked for when stage places the item)

DO DEATH ANIMATION

Game Flow:
Loading Screen > 
Main Menu > 
Character Select Screen OR Options Screen (Language/Instructions) > 
Main Game Screen 
