import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SchoolCardSharedModule } from 'app/shared';
import {
    PressInfComponent,
    PressInfDetailComponent,
    PressInfUpdateComponent,
    PressInfDeletePopupComponent,
    PressInfDeleteDialogComponent,
    pressInfRoute,
    pressInfPopupRoute
} from './';

const ENTITY_STATES = [...pressInfRoute, ...pressInfPopupRoute];

@NgModule({
    imports: [SchoolCardSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PressInfComponent,
        PressInfDetailComponent,
        PressInfUpdateComponent,
        PressInfDeleteDialogComponent,
        PressInfDeletePopupComponent
    ],
    entryComponents: [PressInfComponent, PressInfUpdateComponent, PressInfDeleteDialogComponent, PressInfDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SchoolCardPressInfModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
