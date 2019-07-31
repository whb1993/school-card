import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PressInf } from 'app/shared/model/press-inf.model';
import { PressInfService } from './press-inf.service';
import { PressInfComponent } from './press-inf.component';
import { PressInfDetailComponent } from './press-inf-detail.component';
import { PressInfUpdateComponent } from './press-inf-update.component';
import { PressInfDeletePopupComponent } from './press-inf-delete-dialog.component';
import { IPressInf } from 'app/shared/model/press-inf.model';

@Injectable({ providedIn: 'root' })
export class PressInfResolve implements Resolve<IPressInf> {
    constructor(private service: PressInfService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPressInf> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PressInf>) => response.ok),
                map((pressInf: HttpResponse<PressInf>) => pressInf.body)
            );
        }
        return of(new PressInf());
    }
}

export const pressInfRoute: Routes = [
    {
        path: '',
        component: PressInfComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.pressInf.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PressInfDetailComponent,
        resolve: {
            pressInf: PressInfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.pressInf.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PressInfUpdateComponent,
        resolve: {
            pressInf: PressInfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.pressInf.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PressInfUpdateComponent,
        resolve: {
            pressInf: PressInfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.pressInf.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pressInfPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PressInfDeletePopupComponent,
        resolve: {
            pressInf: PressInfResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'schoolCardApp.pressInf.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
