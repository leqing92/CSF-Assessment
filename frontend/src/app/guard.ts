import { ActivatedRouteSnapshot, CanActivateFn, CanDeactivateFn, RouterStateSnapshot, UrlTree } from "@angular/router"
import { Observable } from "rxjs"
import { PictureComponent } from "./views/picture.component"

export const leavePicture : CanDeactivateFn<PictureComponent> =
    (comp: PictureComponent, route : ActivatedRouteSnapshot, state : RouterStateSnapshot) 
    : boolean | UrlTree | Promise<boolean | UrlTree> | Observable<boolean | UrlTree> => {
        
        return confirm("Are you sure you want to discard the picture??")
    }