import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LostInf } from 'app/shared/model/lost-inf.model';
import { LostInfService } from './lost-inf.service';
import { LostInfComponent } from './lost-inf.component';
import { LostInfDetailComponent } from './lost-inf-detail.component';
import { LostInfUpdateComponent } from './lost-inf-update.component';
import { LostInfDeletePopupComponent } from './lost-inf-delete-dialog.component';
import { ILostInf } from 'app/shared/model/lost-inf.model';

@Injectable({ providedIn: 'root' })
export class LostInfResolve implements Resolve<ILostInf> {
    constructor(private service: LostInfService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILostInf> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LostInf>) => response.ok),
                map((lostInf: HttpResponse<LostInf>) => lostInf.body)
            );
        }
        return of(new LostInf());
    }
}

export const lostInfRoute: Routes = [
    {
        path: '',
        component: LostInfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.lostInf.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LostInfDetailComponent,
        resolve: {
            lostInf: LostInfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.lostInf.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LostInfUpdateComponent,
        resolve: {
            lostInf: LostInfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.lostInf.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LostInfUpdateComponent,
        resolve: {
            lostInf: LostInfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.lostInf.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lostInfPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LostInfDeletePopupComponent,
        resolve: {
            lostInf: LostInfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.lostInf.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
